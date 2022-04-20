package com.example.nuvi_demo.service.board;

import com.example.nuvi_demo.domain.board.Board;
import com.example.nuvi_demo.domain.board.BoardRepository;
import com.example.nuvi_demo.web.dto.board.BoardResponseDto;
import com.example.nuvi_demo.web.dto.board.BoardSaveRequestDto;
import com.example.nuvi_demo.web.dto.board.BoardUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public Long save(BoardSaveRequestDto requestDto) {
        return boardRepository.save(requestDto.toEntity()).getBoard_id();
    }

    @Transactional
    public Long update(Long board_id, BoardUpdateRequestDto requestDto) {
        Board board = boardRepository.findById(board_id).orElseThrow(() -> new IllegalArgumentException("해당 Board는 존재하지 않습니다."));

        board.update(requestDto.getBoard_name(), requestDto.getBoard_type(), requestDto.getRegion_id());
        return board_id;
    }

    public BoardResponseDto findById(Long board_id) {
        Board entity = boardRepository.findById(board_id).orElseThrow(() -> new IllegalArgumentException("해당 Board는 존재하지 않습니다."));
        return new BoardResponseDto(entity);
    }
}

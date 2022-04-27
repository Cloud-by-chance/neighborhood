package com.example.nuvi_demo.web;

import com.example.nuvi_demo.service.board.BoardService;
import com.example.nuvi_demo.web.dto.board.BoardResponseDto;
import com.example.nuvi_demo.web.dto.board.BoardSaveRequestDto;
import com.example.nuvi_demo.web.dto.board.BoardUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BoardApiController {
    private final BoardService boardService;

    @PostMapping("/api/v1/board")
    public Long save(@RequestBody BoardSaveRequestDto requestDto) {
        return boardService.save(requestDto);
    }

    @PutMapping("/api/v1/board/{board_id}")
    public Long update(@PathVariable Long board_id, @RequestBody BoardUpdateRequestDto requestDto) {
        return boardService.update(board_id, requestDto);
    }

    @GetMapping("/api/v1/board/{board_id}")
    public BoardResponseDto findById(@PathVariable("board_id") Long board_id) {
        return boardService.findById(board_id);
    }
}

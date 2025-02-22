pipeline {
  agent any
  environment {
    dockerHubRegistry = 'bluetic321/cicd-back'
    dockerHubRegistryCredential = 'docker-hub-credential'
    githubCredential = 'git-ssh'
  }

  stages {

    stage('Checkout Application Git Branch') {
        steps {
                git url: 'https://github.com/Cloud-by-chance/neighborhood-back.git',
                branch: 'master'
        }
        post {
                failure {
                  echo 'Repository clone failure !'
                }
                success {
                  echo 'Repository clone success !'
                }
        }
    }
/*
    stage('Build and test') {
        agent {
            docker {
                image 'gradle:7.4.2-jdk11'
                args '-v /root/.m2:/root/.m2'
            }
        }
        steps {
<<<<<<< HEAD
	    sh './gradlew clean build'
//            sh 'gradle clean build -b build.gradle'
=======
		sh  '/var/jenkins_home/tools/hudson.plugins.gradle.GradleInstallation/cicd-gradle/bin/gradle clean build'
		//sh 'gradle wrapper'
	        //sh 'chmod +x gradlew'
                //sh  './gradlew clean build'
                //sh  './gradlew --refresh-dependencies'
                //sh  'ls -al ./build'
            //sh 'gradle clean build -b build.gradle --scan'
>>>>>>> 7d5def0c6bad4417097cec75207e4788aa5165ce
        }
    }
*/
    stage('Docker Image Build') {
        steps {
            sh "docker build . -t ${dockerHubRegistry}:${currentBuild.number}"
            sh "docker build . -t ${dockerHubRegistry}:latest"
        }
        post {
                failure {
                  echo 'Docker image build failure !'
                }
                success {
                  echo 'Docker image build success !'
                }
        }
    }
    stage('Docker Image Push') {
        steps {
            withDockerRegistry([ credentialsId: 'docker-hub-credential' , url: "" ]) {
                                sh "docker push ${dockerHubRegistry}:${currentBuild.number}"
                                sh "docker push ${dockerHubRegistry}:latest"

                                sleep 10 /* Wait uploading */ 
                            }
        }
        post {
                failure {
                  echo 'Docker Image Push failure !'
                  sh "docker rmi ${dockerHubRegistry}:${currentBuild.number}"
                  sh "docker rmi ${dockerHubRegistry}:latest"
                }
                success {
                  echo 'Docker image push success !'
                  sh "docker rmi ${dockerHubRegistry}:${currentBuild.number}"
                  sh "docker rmi ${dockerHubRegistry}:latest"
                }
        }
    }  

    stage('K8S Manifest Update') {
        steps {
            git url: 'https://github.com/Cloud-by-chance/neighborhood-manifest.git',
                branch: 'main'

            sh "sed -i 's/cicd-back:.*\$/cicd-back:${currentBuild.number}/g' deployment.yaml"
            sh "git add deployment.yaml"
            sh "git commit -m '[UPDATE] cicd-back ${currentBuild.number} image versioning'"
            sshagent(credentials: ['git-ssh']) {
                sh "git remote set-url origin git@github.com:Cloud-by-chance/neighborhood-manifest.git"
                sh "git push -u origin main"
             }
        }
        post {
                failure {
                  echo 'K8S Manifest Update failure !'
                }
                success {
                  echo 'K8S Manifest Update success !'
                }
        }
    }


  }  
}


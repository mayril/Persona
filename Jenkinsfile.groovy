pipeline {
  agent any

  stages {

    stage('init'){
      steps{
        sh "echo init"
      }
    }
    
    stage('Gradle Build'){
      steps{
        sh "echo build"
        sh "cd ${env.WORKSPACE}/Backend && chmod +x ./gradlew && ./gradlew build"
      }
    }

    stage('Next.JS Image Build') {
      steps {
        script {
          def frontendDir = "${env.WORKSPACE}/Frontend/persona"
          def dockerfile = "${frontendDir}/Dockerfile"
          docker.build("persona-front-image:${env.BUILD_NUMBER}", "-f ${dockerfile} ${frontendDir}")
        }
      }
    }

    stage('Springboot Image Build') {
      steps {
        script {
              def backendDir = "${env.WORKSPACE}/Backend"
              def dockerfile = "${backendDir}/Dockerfile"
              docker.build("my-springboot-image:${env.BUILD_NUMBER}", "-f ${dockerfile} ${backendDir}")

        }
      }
    }

    stage('Remove Docker container') {
      steps {
        script {
          try {
            docker.container(name: 'springboot').stop()
            docker.container(name: 'springboot').remove(force: true)
            docker.container(name: 'frontend').stop()
            docker.container(name: 'frontend').remove(force: true)
          } catch (err) {
            echo "Failed to remove the container"
          }
        }
      }
    }
    
    stage('Run Docker container') {
      steps {
        script {
          docker.image("my-springboot-image:${env.BUILD_NUMBER}").run("--network persona-network --name springboot -p 8080:8080")
          docker.image("persona-front-image:${env.BUILD_NUMBER}").run("--name frontend -p 3000:3000")
        }
      }
    }
  }
}

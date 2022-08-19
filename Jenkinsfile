pipeline {
    agent any
    stages {
        stage('Testing') {
            steps {
              echo 'Running Test'
              sh './mvnw test'
              junit '**/target/surefire-reports/TEST-*.xml'
            }
        }
        stage('Spotless Check') {
            steps {
               echo 'Spotless Check'
               sh './mvnw spotless:check'
            }
        }
        stage('Verify Coverage') {
            steps {
               echo 'Verifying coverage'
               jacoco()
            }
        }
    }
    post {

       always{
          sh 'echo "Pipeline complete" '
       }
    }
}

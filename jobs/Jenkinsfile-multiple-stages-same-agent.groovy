pipeline {
    agent none

    stages {
        stage("build and test the project") {
            agent {
                kubernetes {
                    yamlFile 'resources/yaml/podTemplate-maven.yml'
                }
            }
            stages {
                stage("build") {
                    steps {
                        sh "echo build"
                    }
                }
                stage("test") {
                    steps {
                        sh "echo test"
                    }
                }
            }
            post {
                success {
                    sh "echo post success"
                }
            }
        }

        stage("deploy the artifacts if a user confirms") {
            agent {
                kubernetes {
                    yamlFile 'resources/yaml/podTemplate-customagent.yml'
                }
            }
            steps {
                sh "echo deploy"
            }
        }
    }
}

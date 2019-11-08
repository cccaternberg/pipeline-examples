pipeline {
    agent {
        kubernetes {
            yamlFile 'resources/yaml/podTemplate.yml'
        }
    }
    stages {
        stage('Say Hello') {
            steps {
                container('maven') {
                    echo 'Hello World!'
                }
            }
        }
    }
}

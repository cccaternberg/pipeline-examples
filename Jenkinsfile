pipeline {
    agent {
        kubernetes {
            yamlFile 'resources/yaml/podTemplate-gradle.yml'
        }
    }
    stages {
        stage('buildLib') {
            steps {
                container('gradle') {
                    script {
                        println System.getProperty("java.ext.dirs")
                    }
                    sh 'gradle clean lib'
                    sh 'mkdir -p  ~/.groovy/grapes/  &&  cp -f lib/*.jar  ~/.groovy/grapes/ && chmod -R 755  lib/ && chmod -R 755 ~/.groovy/grapes/'
                    sh "echo $CLASSPATH"
                    script {
                    //If agent other than "any" the ìput`should take place outside a agent definition!!!
                    returnValue = input message: 'Need some input'
                     }
                echo "${returnValue}"
                    withCredentials([string(credentialsId: 'githubaccesstoken', variable: 'GH_ACCESS_TOKEN')]) {
                        echo sh(script: 'env|sort', returnStdout: true)
                        jobDsl targets: ['resources/groovy/Seed.groovy'].join('\n'),
                                removedJobAction: 'DELETE',
                                removedViewAction: 'DELETE',
                                lookupStrategy: 'SEED_JOB',
                                additionalClasspath: ['/home/gradle/.groovy/grapes/**/*.jar'].join('\n'),
                                additionalParameters: [credentials: "${GH_ACCESS_TOKEN}"]
                    }
                    // point to exact source file
                 /*
                    script {
                        withCredentials([string(credentialsId: 'githubaccesstoken', variable: 'GH_ACCESS_TOKEN')]) {
                            apiKey = "\nAPI key: ${GH_ACCESS_TOKEN}\n"
                            println apiKey
                            echo env.GH_ACCESS_TOKEN
                            def rootDir = pwd()
                            def seed = load "${rootDir}/Seed.groovy"
                            seed.createPipelineJobs(env.GH_ACCESS_TOKEN)
                        }
                    }
                    */

                }
            }
        }
    }
}

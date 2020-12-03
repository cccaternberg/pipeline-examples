library '_github_com_pipeline-templates-apps_pipeline-library' _
def maven = libraryResource 'podtemplates/podTemplate-os-tools.yaml'
pipeline {
    agent {
        kubernetes {
            label 'mymavenlabel'
            yaml maven
        }
    }
    stages {
        stage('Download and modify Update-Center Data') {
            steps {
                // url: "https://jenkins-updates-cdn.cloudbees.com/updateCenter/5ByTxYGA/update-center.json?cacheKey=1606296257000&id=virtual-cap-core-mm",
                // url: "https://updates.jenkins.io/update-center.json?id=default&amp;version=" + Jenkins.instance.version,

                sh "curl -v -o update-center-original.json https://jenkins-updates-cdn.cloudbees.com/updateCenter/5ByTxYGA/update-center.json?cacheKey=1606296257000&id=virtual-cap-core-mm"
                sh "ls -l"
                sh "cat update-center-original.json | jq "
                script {
                    updateCenterJson = readFile file: 'update-center-original.json'
                    updateCenterJson = updateCenterJson.replaceAll("http:\\/\\/updates\\.jenkins-ci\\.org\\/download\\/", "http://archives.jenkins-ci.org/")
                }
                writeFile text: updateCenterJson, file: 'update-center.json'
                sh "ls -l"
                sh "diff update-center-original.json update-center.json"
                echo "##############################"
                sh "cat update-center.json | jq"
                archiveArtifacts 'update-center.json'
            }
        }
    }
}
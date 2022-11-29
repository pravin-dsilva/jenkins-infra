def call(){
    script {
        ansiColor('xterm') {
            echo ""
        }
        try {
            // Run the script for deploying hypershift
            sh '''
                TAG="ppc64le"
                rm -rf /usr/local/go
                wget https://go.dev/dl/go1.18.8.darwin-amd64.tar.gz
                tar -C /usr/local -xzf go1.18.8.darwin-amd64.tar.gz
                export PATH=/usr/local/go/bin:$PATH
                go version
                cd ocp4-playbooks-extras
                cp examples/hypershift_install.yaml vars.yaml
                echo $SERVICE_INSTANCE_ID
            '''
        }
        catch (err){
            env.FAILED_STAGE=env.STAGE_NAME
            throw err
        }
    }
}

def call(){
    script {
        ansiColor('xterm') {
            echo ""
        }
        try {
            // Run the script for deploying hypershift
            sh '''
                TAG="ppc64le"
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

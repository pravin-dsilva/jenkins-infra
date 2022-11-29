def call(){
    script {
        ansiColor('xterm') {
            echo ""
        }
        try {
            // Run the script for deploying hypershift
            sh '''
                TAG="ppc64le"
                cat /etc/os-release
                wget https://storage.googleapis.com/golang/getgo/installer_linux
                chmod +x ./installer_linux; ./installer_linux 
                source ~/.bash_profile
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

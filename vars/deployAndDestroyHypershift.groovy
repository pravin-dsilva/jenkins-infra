def call(){
    script {
        ansiColor('xterm') {
            echo ""
        }
        try {
            // Run the script for deploying hypershift
            sh '''
                TAG="ppc64le"
                arch
                rm -rf /usr/local/go
                export IBMCLOUD_API_KEY=$IBMCLOUD_API_KEY
                cat $PULL_SECRET > ~/.pullSecret
                wget https://go.dev/dl/go1.18.8.linux-amd64.tar.gz
                tar -C /usr/local -xzf go1.18.8.linux-amd64.tar.gz
                export PATH=/usr/local/go/bin:$PATH
                go version
                cd ocp4-playbooks-extras
                cp examples/hypershift_install.yaml vars.yaml
                sed -i "s|powervs_region:.*$|powervs_region: $POWERVS_REGION |g" vars.yaml
                sed -i "s|powervs_zone:.*$|powervs_zone: $POWERVS_ZONE|g" vars.yaml
                sed -i "s|ibmcloud_resource_group:.*$|ibmcloud_resource_group: $IBMCLOUD_RESOURCE_GROUP |g" vars.yaml
                sed -i "s|ocp_release_image:.*$|ocp_release_image: $OCP_RELEASE_IMAGE |g" vars.yaml
                ansible-playbook -i inventory -e @vars.yaml playbooks/hypershift.yml  
            '''
        }
        catch (err){
            env.FAILED_STAGE=env.STAGE_NAME
            throw err
        }
    }
}

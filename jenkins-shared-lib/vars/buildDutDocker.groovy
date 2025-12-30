// vars/buildCtrlDocker.groovy
def call(ciConfig) {

    def ctrlCfg = ciConfig.ci_config.build_config.ctrl
    def dockerImage = ctrlCfg.docker_image ?: error("CTRL docker_image missing")

    stage('Build CTRL in Docker') {

        def arch = sh(script: "uname -m", returnStdout: true).trim()
        def dockerPlatform = (arch == "x86_64") ? "linux/amd64" : "linux/arm64"

        sh """#!/bin/bash
            set -ex
            docker run --rm --user root --platform=${dockerPlatform} \\
              -v \$PWD/ctrl_sdk:/home/connectedhome \\
              -w /home/connectedhome ${dockerImage}:latest \\
              /bin/bash -c "
                ./scripts/examples/gn_build_example.sh \\
                examples/all-clusters-app/linux \\
                out/all-clusters-app \\
                chip_inet_config_enable_ipv4=false chip_device_config_enable_wifipaf=true
            "
        """
    }
}

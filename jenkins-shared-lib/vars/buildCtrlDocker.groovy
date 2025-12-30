// vars/buildCtrlDocker.groovy
def call(ciConfig) {

    def dockerImage = ciConfig.ci_config.build_config.ctrl.docker_image ?: "chip_image:latest"
    def workSpace   = pwd()

    stage('Build CTRL (Docker)') {

        def arch = sh(script: "uname -m", returnStdout: true).trim()
        def dockerPlatform = (arch == "x86_64") ? "linux/amd64" : "linux/arm64"

        echo "CTRL Docker Image   : ${dockerImage}"
        echo "Docker Platform     : ${dockerPlatform}"
        echo "Workspace           : ${workSpace}"

        sh """#!/bin/bash
        set -euxo pipefail

        docker run --rm --user root --platform=${dockerPlatform} \\
          -v "${workSpace}/ctrl_sdk:/home/connectedhome" \\
          -w /home/connectedhome \\
          ${dockerImage} \\
          /bin/bash -c '
            set -euxo pipefail

            git config --global --add safe.directory /home/connectedhome
            git config --global --add safe.directory /home/connectedhome/third_party/pigweed/repo

            ./scripts/checkout_submodules.py \\
              --allow-changing-global-git-config \\
              --shallow \\
              --platform linux

            source scripts/bootstrap.sh
            source scripts/activate.sh

            ./scripts/examples/gn_build_example.sh \\
              examples/chip-tool \\
              out/chip-tool \\
              "chip_mdns=\\"platform\\" chip_inet_config_enable_ipv4=false"
          '
        """
    }
}

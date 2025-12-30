def call(ciConfig) {

    def dockerImage = ciConfig.ci_config.build_config.ctrl.docker_image ?: "chip_image:latest"
    def workSpace   = pwd()

    stage('Build CTRL (Docker)') {

        def arch = sh(script: "uname -m", returnStdout: true).trim()
        def dockerPlatform = (arch == "x86_64") ? "linux/amd64" : "linux/arm64"

        echo "CTRL Docker Image   : ${dockerImage}"
        echo "Docker Platform     : ${dockerPlatform}"
        echo "Workspace           : ${workSpace}"

        def dockerCmd = """#!/bin/bash
            set -euo pipefail

            docker run --rm \\
              --platform=${dockerPlatform} \\
              -v "${workSpace}/ctrl_sdk:/home/connectedhome" \\
              -w /home/connectedhome \\
              ${dockerImage} \\
              /bin/bash -c '
                set -ex
                scripts/examples/gn_build_example.sh \\
                  examples/chip-tool \\
                  out/chip-tool \\
                  chip_mdns="platform" \\
                  chip_inet_config_enable_ipv4=false
              '
        """

        sh dockerCmd
    }
}

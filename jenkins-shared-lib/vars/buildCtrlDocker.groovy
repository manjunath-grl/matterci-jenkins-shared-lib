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
            set -ex

            docker run --rm --user root --platform=${dockerPlatform} -v ${workSpace}/ctrl_sdk:/home/connectedhome \\
            -w /home/connectedhome ${dockerImage}:latest \\
            /bin/bash -c \'
                set -ex  # Stop execution on first error
                git config --global --add safe.directory /home/connectedhome
                git config --global --add safe.directory /home/connectedhome/third_party/pigweed/repo
                git config --global http.version HTTP/1.1
                git config --global http.postBuffer 524288000
                git config --global http.lowSpeedLimit 0
                git config --global http.lowSpeedTime 999999
                ./scripts/checkout_submodules.py --allow-changing-global-git-config --shallow --platform linux
                source scripts/bootstrap.sh
                source scripts/activate.sh
                ./scripts/examples/gn_build_example.sh examples/chip-tool out/chip-tool chip_mdns="platform" chip_inet_config_enable_ipv4=false
              \'
        """

        sh dockerCmd
    }
}

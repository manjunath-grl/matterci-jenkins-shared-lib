def call(ciConfig) {

    def dutCfg      = ciConfig.ci_config.build_config.dut
    def dockerImage = dutCfg.docker_image ?: "chip_image"
    def workSpace   = pwd()

    stage('Build DUT (Docker)') {

        def arch = sh(script: "uname -m", returnStdout: true).trim()
        def dockerPlatform = (arch == "x86_64") ? "linux/amd64" : "linux/arm64"

        echo "DUT Docker Image : ${dockerImage}"
        echo "Docker Platform  : ${dockerPlatform}"
        echo "Workspace        : ${workSpace}"

        sh """#!/bin/bash
        set -ex

        docker run --rm --user root --platform=${dockerPlatform} -v "${workSpace}/dut_sdk:/home/connectedhome" \\
        -w /home/connectedhome ${dockerImage} \\
        /bin/bash -c \'
            set -ex
            git config --global --add safe.directory /home/connectedhome
            git config --global --add safe.directory /home/connectedhome/third_party/pigweed/repo
            git config --global http.version HTTP/1.1
            git config --global http.postBuffer 524288000
            git config --global http.lowSpeedLimit 0
            git config --global http.lowSpeedTime 999999

            ./scripts/checkout_submodules.py --allow-changing-global-git-config --shallow --platform linux
            source scripts/bootstrap.sh
            source scripts/activate.sh

            scripts/build/build_examples.py --target linux-arm64-all-clusters-ipv6only build
          \'
        """
    }
}

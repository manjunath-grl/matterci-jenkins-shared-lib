// vars/buildCtrlDocker.groovy
def call(ciConfig) {

    def ctrlCfg = ciConfig.ci_config.build_config.ctrl
    def dockerImage = ctrlCfg.docker_image ?: error("CTRL docker_image missing")

    stage('Build CTRL in Docker') {

        def arch = sh(script: "uname -m", returnStdout: true).trim()
        def dockerPlatform = (arch == "x86_64") ? "linux/amd64" : "linux/arm64"

        sh """#!/bin/bash
            set -ex
            docker run --rm --user root --platform=${dockerPlatform} -v ${workSpace}/ctrl_sdk:/home/connectedhome \\
            -w /home/connectedhome ${dockerImage}:latest \\
            /bin/bash -c \"
                set -ex  # Stop execution on first error
                git config --global --add safe.directory /home/connectedhome
                git config --global --add safe.directory /home/connectedhome/third_party/pigweed/repo
                git config --global http.version HTTP/1.1
                git config --global http.postBuffer 524288000
                git config --global http.lowSpeedLimit 0
                git config --global http.lowSpeedTime 999999
                ./scripts/checkout_submodules.py --allow-changing-global-git-config --shallow --platform linux
                source scripts/bootstrap.sh
                pw cli-analytics --opt-out
                source scripts/activate.sh
                # TODO: -n false is a temporary workaround needs to be updated it to dynamic bases on the configuration.
                scripts/build_python.sh -m platform -d true -i out/python_env -n false
                ./scripts/examples/gn_build_example.sh examples/all-clusters-app/linux out/all-clusters-app chip_inet_config_enable_ipv4=false chip_device_config_enable_wifipaf=true
            \"
        """
    }
}

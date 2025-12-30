def call(ciConfig) {

    def ctrlCfg = ciConfig.ci_config.build_config.ctrl
    def workSpace = pwd()
    def jobName = env.JOB_NAME.replaceAll('/', '_')

    stage('Build CTRL (Docker)') {

        def arch = sh(script: "uname -m", returnStdout: true).trim()
        def dockerPlatform = (arch == "x86_64") ? "linux/amd64" : "linux/arm64"

        echo "Workspace : ${workSpace}"

        sh """
        cd ${jobName}/ctrl_sdk
        ./scripts/pi-setup/auto-install.sh
        """
    }
}

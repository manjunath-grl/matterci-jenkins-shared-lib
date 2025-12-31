def call(ciConfig) {

    def ctrlCfg = ciConfig.ci_config.build_config.ctrl
    def workSpace = pwd()
    def jobName = env.JOB_NAME.replaceAll('/', '_')

    stage('Build CTRL (Docker)') {

        echo "Workspace : ${workSpace}"

        sh """
            cd ctrl_sdk/

            chmod +x cli/scripts/th_cli_install.sh

            sudo apt update
            sudo apt install -y pipx python3-venv

            pipx ensurepath
            pipx install poetry

            export PATH="\$HOME/.local/bin:\$PATH"

            ./cli/scripts/th_cli_install.sh
            """

    }
}

def call(type, cfg) {
    echo "Copying artifacts for ${type}"
    def cfgBlock = cfg.ci_config.copy_build_artifact.${type}
    echo "Config block: ${cfgBlock}"
    def projectName = env.JOB_NAME
    def selector = env.BUILD_NUMBER
    echo "Copying artifacts for ${type} from job: ${projectName}, build: ${selector}"
    echo "Config enabled: ${cfgBlock.enabled}"
    if (cfgBlock.enabled) {
        projectName = cfgBlock.job_name
        echo "Overriding project name to: ${projectName}"
        selector = cfgBlock.build_number
        echo "Overriding build number to: ${selector}"
    }
    step([
        $class: 'CopyArtifact',
        projectName: projectName,
        selector: specific(selector),
        filter: "**/*",
        target: "${type}_artifacts"
    ])
}

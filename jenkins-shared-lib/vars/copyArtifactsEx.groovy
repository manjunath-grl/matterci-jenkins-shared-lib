def call(type, cfg) {
    echo "Copying artifacts for ${type}"

    def cfgBlock = cfg.ci_config.copy_build_artifact[type]
    if (!cfgBlock) {
        error "No copy_build_artifact config found for type: ${type}"
    }

    echo "Config block: ${cfgBlock}"

    def projectName = env.JOB_NAME
    def selector = specific(env.BUILD_NUMBER)

    echo "Copying artifacts for ${type} from job: ${projectName}, build: ${env.BUILD_NUMBER}"
    echo "Config enabled: ${cfgBlock.enabled}"

    if (cfgBlock.enabled) {
        projectName = cfgBlock.job_name
        selector = specific(cfgBlock.build_number)

        echo "Overriding project name to: ${projectName}"
        echo "Overriding build number to: ${cfgBlock.build_number}"
    }

    step([
        $class: 'CopyArtifact',
        projectName: projectName,
        selector: selector,
        filter: "**/*",
        target: "${type}_artifacts"
    ])
}

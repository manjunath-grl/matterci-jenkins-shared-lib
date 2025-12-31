def call(type, cfg) {
    def cfgBlock = cfg.ci_config.copy_build_artifact
    def projectName = env.JOB_NAME
    def selector = env.BUILD_NUMBER
    if (cfgBlock.${type}.enabled) {
        projectName = cfgBlock.${type}.job_name
        selector = cfgBlock.${type}.build_number
    }
    step([
        $class: 'CopyArtifact',
        projectName: projectName,
        selector: specific(selector),
        filter: "**/*",
        target: "${type}_artifacts"
    ])
}

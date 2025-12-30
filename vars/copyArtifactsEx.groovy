def call(type, cfg) {
    def cfgBlock = cfg.ci_config.copy_build_artifact[type.toLowerCase()]
    step([
        $class: 'CopyArtifact',
        projectName: cfgBlock.job_name,
        selector: specific(cfgBlock.build_number.toString()),
        filter: "**/*",
        target: "${type}_artifacts"
    ])
}

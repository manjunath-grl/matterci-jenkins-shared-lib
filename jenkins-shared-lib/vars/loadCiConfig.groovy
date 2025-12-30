def call(String path = 'resources/ci_config.yaml') {
    return readYaml(file: path)
}
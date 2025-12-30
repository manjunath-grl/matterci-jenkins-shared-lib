def call(String path = null) {
    if (path && fileExists(path)) {
        echo "Loading CI config from external path: ${path}"
        return readYaml(file: path)
    }

    echo "Loading CI config from shared library resources"
    def yamlText = libraryResource 'ci_config.yaml'
    return readYaml(text: yamlText)
}

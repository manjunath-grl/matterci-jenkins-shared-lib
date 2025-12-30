def call(testCfg) {
    testCfg.YAML.each {
        sh "python3 run_test.py --yaml ${it}"
    }
    testCfg.PYTHON.each {
        sh "python3 ${it}"
    }
}

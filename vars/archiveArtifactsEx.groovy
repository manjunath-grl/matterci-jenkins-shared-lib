def call(type) {
    def dirName = type == "CTRL" ? "ctrl_sdk/out" : "dut_sdk/out"
    archiveArtifacts artifacts: "${dirName}/**", fingerprint: true
}

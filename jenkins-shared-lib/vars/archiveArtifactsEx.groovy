def call(type) {
    def dirName = type == "CTRL" ? "ctrl_sdk/*" : "dut_sdk/out/linux-arm64-*"
    archiveArtifacts artifacts: "${dirName}/**", fingerprint: true
}

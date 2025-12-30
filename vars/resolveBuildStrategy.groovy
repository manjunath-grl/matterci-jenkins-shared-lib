def call(cfg) {
    return [
        ctrl: [
            build: cfg.ci_config.build_config.ctrl.build_enabled
        ],
        dut: [
            build: cfg.ci_config.build_config.dut.build_enabled
        ]
    ]
}

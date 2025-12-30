def call(type) {
    sh """
    chmod +x ${type}_artifacts/**
    """
}

class NetworkModel {
  NetworkModel({
    required this.networkType,
    required this.isConnected,
  });
  final NetworkType networkType;
  final bool isConnected;

  factory NetworkModel.fromMap(Map<String, dynamic> data) {
    return NetworkModel(
      networkType: NetworkType.fromString(data["connectedTo"]),
      isConnected: data["status"] == "Connected",
    );
  }

  @override
  String toString() =>
      'NetworkModel(networkType: ${networkType.rawValue}, isConnected: $isConnected)';
}

enum NetworkType {
  wifi("Wifi"),
  cellular("Cellular"),
  none("None");

  final String rawValue;
  const NetworkType(this.rawValue);

  factory NetworkType.fromString(String val) {
    if (val == wifi.rawValue) return wifi;
    if (val == cellular.rawValue) return cellular;
    return none;
  }
}

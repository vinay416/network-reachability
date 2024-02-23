import 'package:network_check/model/network_model.dart';

import 'network_check_platform_interface.dart';

class NetworkCheck {
  Future<NetworkModel> getConnectivityStatus({bool debugMode = false}) async {
    final data = await NetworkCheckPlatform.instance.getConnectivityStatus();
    if (data == null) throw Exception("Error on API call data");
    final mapData = Map<String, dynamic>.from(data as Map);
    return NetworkModel.fromMap(mapData);
  }

  Stream<NetworkModel> connectivityStream({bool debugMode = false}) {
    return NetworkCheckPlatform.instance
        .getConnectivityStream(debugMode: debugMode)
        .asBroadcastStream()
        .map((event) {
      final mapData = Map<String, dynamic>.from(event as Map);
      return NetworkModel.fromMap(mapData);
    });
  }
}

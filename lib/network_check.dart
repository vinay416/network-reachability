import 'package:network_check/model/network_model.dart';

import 'network_check_platform_interface.dart';

class NetworkCheck {
  Future<NetworkModel> getConnectivityStatus() async {
    final data = await NetworkCheckPlatform.instance.getConnectivityStatus();
    if (data == null) throw Exception("Error on API call data");
    final mapData = Map<String, dynamic>.from(data as Map);
    return NetworkModel.fromMap(mapData);
  }

  Stream<NetworkModel> get connectivityStream {
    return NetworkCheckPlatform.instance.getConnectivityStream().map((event) {
      final mapData = Map<String, dynamic>.from(event as Map);
      return NetworkModel.fromMap(mapData);
    });
  }
}

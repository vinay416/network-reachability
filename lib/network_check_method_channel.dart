import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'network_check_platform_interface.dart';

/// An implementation of [NetworkCheckPlatform] that uses method channels.
class MethodChannelNetworkCheck extends NetworkCheckPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('network_check');

  @override
  Future<dynamic> getConnectivityStatus() async {
    final data = await methodChannel.invokeMethod('getConnectivityStatus');
    return data;
  }
}

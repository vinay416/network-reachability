import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'network_check_platform_interface.dart';

/// An implementation of [NetworkCheckPlatform] that uses method channels.
class MethodChannelNetworkCheck extends NetworkCheckPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('network_check');

  @visibleForTesting
  final eventChannel = const EventChannel('network_check_stream');
  // timeHandlerEvent event name . it should be same on android , IOS and Flutter

  final debugKey = 'debugMode';

  @override
  Future<dynamic> getConnectivityStatus({bool debugMode = false}) async {
    final data = await methodChannel.invokeMethod('getConnectivityStatus');
    return data;
  }

  @override
  Stream<dynamic> getConnectivityStream({bool debugMode = false}) {
    return eventChannel.receiveBroadcastStream({debugKey: debugMode});
  }
}

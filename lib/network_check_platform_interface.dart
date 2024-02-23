import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'network_check_method_channel.dart';

abstract class NetworkCheckPlatform extends PlatformInterface {
  /// Constructs a NetworkCheckPlatform.
  NetworkCheckPlatform() : super(token: _token);

  static final Object _token = Object();

  static NetworkCheckPlatform _instance = MethodChannelNetworkCheck();

  /// The default instance of [NetworkCheckPlatform] to use.
  ///
  /// Defaults to [MethodChannelNetworkCheck].
  static NetworkCheckPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [NetworkCheckPlatform] when
  /// they register themselves.
  static set instance(NetworkCheckPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<dynamic> getConnectivityStatus() {
    throw UnimplementedError(
      'getConnectivityStatus() has not been implemented.',
    );
  }

  Stream<dynamic> getConnectivityStream({bool debugMode = false}) {
    throw UnimplementedError(
      'getConnectivityStream() has not been implemented.',
    );
  }
}

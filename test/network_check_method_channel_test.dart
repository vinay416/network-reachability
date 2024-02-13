import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:network_check/network_check_method_channel.dart';

void main() {
  TestWidgetsFlutterBinding.ensureInitialized();

  MethodChannelNetworkCheck platform = MethodChannelNetworkCheck();
  const MethodChannel channel = MethodChannel('network_check');

  setUp(() {
    TestDefaultBinaryMessengerBinding.instance.defaultBinaryMessenger.setMockMethodCallHandler(
      channel,
      (MethodCall methodCall) async {
        return '42';
      },
    );
  });

  tearDown(() {
    TestDefaultBinaryMessengerBinding.instance.defaultBinaryMessenger.setMockMethodCallHandler(channel, null);
  });

  test('getPlatformVersion', () async {
    expect(await platform.getConnectivityStatus(), '42');
  });
}

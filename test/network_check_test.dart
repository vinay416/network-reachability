// import 'package:flutter_test/flutter_test.dart';
// import 'package:network_check/network_check.dart';
// import 'package:network_check/network_check_platform_interface.dart';
// import 'package:network_check/network_check_method_channel.dart';
// import 'package:plugin_platform_interface/plugin_platform_interface.dart';

// class MockNetworkCheckPlatform
//     with MockPlatformInterfaceMixin
//     implements NetworkCheckPlatform {

//   @override
//   Future<String?> getConnectivityStatus() => Future.value('Connected');
// }

// void main() {
//   final NetworkCheckPlatform initialPlatform = NetworkCheckPlatform.instance;

//   test('$MethodChannelNetworkCheck is the default instance', () {
//     expect(initialPlatform, isInstanceOf<MethodChannelNetworkCheck>());
//   });

//   test('getConnectivityStatus', () async {
//     NetworkCheck networkCheckPlugin = NetworkCheck();
//     MockNetworkCheckPlatform fakePlatform = MockNetworkCheckPlatform();
//     NetworkCheckPlatform.instance = fakePlatform;

//     expect(await networkCheckPlugin.getConnectivityStatus(), 'Connected');
//   });
// }

import Flutter
import UIKit

public class NetworkCheckPlugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "network_check", binaryMessenger: registrar.messenger())
    let instance = NetworkCheckPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    switch call.method {
    case "getConnectivityStatus":
        let networkData = NetworkReachability().isNetworkAvailable()
        guard let networkData else {
            return result(
                FlutterError( code: "-1",
                            message: "API broken",
                            details: "Internal Api return null" )
            )
        }
        result(networkData.toMap())
    default:
      result(FlutterMethodNotImplemented)
    }
  }
}

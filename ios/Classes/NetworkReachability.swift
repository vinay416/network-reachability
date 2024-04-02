//
//  NetworkReachability.swift
//  Runner
//
//  Created by Vinay Sharma on 27/02/24.
//
import Foundation
import Network
import OSLog

class NetworkReachability {

    var pathMonitor: NWPathMonitor!
    var path: NWPath?
    lazy var pathUpdateHandler: ((NWPath) -> Void) = { path in
        self.path = path
        if path.status == NWPath.Status.satisfied {
            print("Connected")
        } else if path.status == NWPath.Status.unsatisfied {
            print("unsatisfied")
        } else if path.status == NWPath.Status.requiresConnection {
            print("requiresConnection")
        }
    }

    let backgroudQueue = DispatchQueue.global(qos: .background)

    init() {
        print("init")
        pathMonitor = NWPathMonitor()
        pathMonitor.pathUpdateHandler = self.pathUpdateHandler
        pathMonitor.start(queue: backgroudQueue)
    }

    func isNetworkAvailable() -> NetworkModel? {
        
        guard let path = self.path else {
            return nil
        }
        var networkType : NetworkType = .none
        let isCellular = path.usesInterfaceType(.cellular);
        let isWifi = path.usesInterfaceType(.wifi)
            
        if isWifi {
            networkType = .wifi
        }else if isCellular {
            networkType = .cellular
        }else {
            networkType = .none
        }
        
        if path.status == NWPath.Status.satisfied {
            return NetworkModel(type: networkType, connected: true)
        }else {
            return NetworkModel(type: networkType, connected: false)
        }

    }
}


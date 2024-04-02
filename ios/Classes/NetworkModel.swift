//
//  NetworkModel.swift
//  Runner
//
//  Created by Vinay Sharma on 27/02/24.
//

import Foundation
import Network

class NetworkModel{
    private let connectedTo : NetworkType
    private let status : Bool
    
    init(type : NetworkType, connected : Bool) {
        connectedTo = type
        status = connected
    }
    
    func toMap() -> [String:Any]{
        return [
            "connectedTo" : connectedTo.rawValue,
            "status" : status,
        ]
    }
}

enum NetworkType : String {
    case wifi = "Wifi"
    case cellular = "Cellular"
    case none = "None"
    
    static func fromString(type: String) -> NetworkType{
        switch type {
        case NetworkType.wifi.rawValue:
            return .wifi
        case NetworkType.cellular.rawValue:
            return .cellular
        default: return .none
        }
    }
}


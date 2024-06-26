import 'dart:developer';

import 'package:flutter/material.dart';
import 'dart:async';

import 'package:network_check/network_check.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String? platformStatus = 'Not connected';
  final networkCheckPlugin = NetworkCheck();

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    try {
      final res = await networkCheckPlugin.getConnectivityStatus();
      print(res.toString());
    } catch (e) {
      log("Error $e");
    }
    if (!mounted) return;
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        // body: StreamBuilder(
        //   stream: networkCheckPlugin.connectivityStream(),
        //   builder: (context, snapshot) {
        //     if (snapshot.hasError) {
        //       return const Text("Stream error");
        //     }
        //     if (snapshot.hasData) {
        //       return Text(snapshot.data?.toString() ?? "Data error");
        //     }
        //     return const CircularProgressIndicator();
        //   },
        // ),
      ),
    );
  }
}

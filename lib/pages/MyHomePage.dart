import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:thirty_day_of_flutter/models/photo.dart';
import 'package:thirty_day_of_flutter/widgets/themes.dart';
import 'package:velocity_x/velocity_x.dart';

class HomePage extends StatefulWidget {
  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  final int days = 30;
  final String name = "CodePur";

  @override
  void initState() {
    super.initState();
    loadData();
  }

  loadData() async {
    await Future.delayed(Duration(seconds: 2));
    final photoJson = await rootBundle.loadString("assets/files/photo.json");
    final decodedData = jsonDecode(photoJson);
    PhotoModel.photos = List.from(decodedData)
        .map<Photo>((photo) => Photo.fromMap(photo))
        .toList();
    setState(() {});
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        backgroundColor: MyTheme.creamColor,
        // appBar: AppBar(
        //   centerTitle: true,
        //   title: Text("30 Days of Flutter"),
        // ),
        body: SafeArea(
          child: Container(
            padding: Vx.m32,
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                PhotoHeader(),
                if (PhotoModel.photos != null && PhotoModel.photos.isNotEmpty)
                  PhotosList().expand()
                else
                  Center(
                    child: CircularProgressIndicator(),
                  )
              ],
            ),
          ),
        ));
  }
}

class PhotosList extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return ListView.builder(
      shrinkWrap: true,
      itemCount: PhotoModel.photos.length,
      itemBuilder: (context, index) {
        final photo = PhotoModel.photos[index];
        return PhotoItem(
          photo: photo,
        );
      },
    );
  }
}

class PhotoItem extends StatelessWidget {
  final Photo photo;

  const PhotoItem({Key key, @required this.photo}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return VxBox(
        child: Row(
      children: [
        PhotoWidget(image: photo.url),
        Expanded(
            child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            photo.title.text.bold.color(MyTheme.darkBluishColor).make(),
            photo.id.text.textStyle(context.captionStyle).make(),
            ButtonBar(
              alignment: MainAxisAlignment.spaceBetween,
              children: [
                "\$${photo.id}".text.bold.xl.make(),
                ElevatedButton(
                  onPressed: () {},
                  child: "Buy".text.make(),
                  style: ButtonStyle(
                    backgroundColor: MaterialStateProperty.all(MyTheme.darkBluishColor),
                    shape: MaterialStateProperty.all(StadiumBorder())
                  )
                )
              ],
            )
          ],
        ))
      ],
    )).white.roundedLg.square(151.0).make().py16();
  }
}

class PhotoWidget extends StatelessWidget {
  final String image;

  const PhotoWidget({Key key, @required this.image}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.all(8.0),
      child: Image.network(image)
          .box
          .rounded
          .p16
          .color(MyTheme.creamColor)
          .make()
          .w32(context),
    );
  }
}

class PhotoHeader extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        "Photo App".text.xl4.bold.color(MyTheme.darkBluishColor).make(),
        "Trending products".text.xl2.make(),
      ],
    );
  }
}

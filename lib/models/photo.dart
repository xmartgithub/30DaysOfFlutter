//model class
class Photo {
  final num albumId;
  final num id;
  final String title;
  final String thumbnailUrl;

  Photo({this.albumId, this.id, this.title, this.thumbnailUrl});
}

final products = [
  Photo(
    albumId: 1,
    id: 1,
    title: "accusamus beatae ad facilis cum similique qui sunt",
    thumbnailUrl: "https://via.placeholder.com/150/92c952",
  )
];

struct ConnectionInfo {
  1:string nodeIp,
  2:i32 nodePort,
}

struct FileInfo {
  1:string fileName,
  2:string fileContent,
  3:i32 fileVersion
}

service ServerService {
  bool join(1: ConnectionInfo node),
  bool setNwNr(1: i32 nw, 2: i32 nr, 3: string mode),
  bool write(1: string fileName, 2: string fileContent, 3: string mode),
  void update(1: FileInfo curFile),
  void upload(1: string fileName, 2: string fileContent, 4: string mode),
  FileInfo read(1: string fileName, 2: string mode)
}

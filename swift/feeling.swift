class Master {
    var nman: Int = 0
    var nwoman: Int = 0
    var manList: [Player] = []
    var womanList: [Player] = []
}
class Player {
    var name: String = "名前不明"
    var sex: String = "性別不明"

    init(name: String, sex: String) {
        self.name = name
        self.sex = sex
    }
}


//--------------- Main Routin ----------------//
let person = Player(name: "晃輝", sex: "man")

let standardInput = NSFileHandle.fileHandleWithStandardInput()
let input = standardInput.availableData
let datastring = String(NSString(data:input, encoding:NSUTF8StringEncoding))
print(datastring)

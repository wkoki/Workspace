field = [[0, 0, 0], [0, 0, 0], [0, 0, 0]]

kokivalue = 1
masashivalue = 2

while True:
    print("========================")
    print("+-+-+-+")
    print("|{0}|{1}|{2}|".format(field[0][0], field[0][1], field[0][2])
    print("+-+-+-+")
    print("|{0}|{1}|{2}|".format(field[1][0], field[1][1], field[1][2])
    print("+-+-+-+")
    print("|{0}|{1}|{2}|".format(field[2][0], field[2][1], field[2][2])
    print("+-+-+-+")
    print("========================")

    kokix = input("x: ")
    kokiy = input("y: ")
    field[kokix][kokiy] = kokivalue

    print("========================")
    print("+-+-+-+")
    print("|{0}|{1}|{2}|".format(field[0][0], field[0][1], field[0][2])
    print("+-+-+-+")
    print("|{0}|{1}|{2}|".format(field[1][0], field[1][1], field[1][2])
    print("+-+-+-+")
    print("|{0}|{1}|{2}|".format(field[2][0], field[2][1], field[2][2])
    print("+-+-+-+")
    print("========================")

    masashix = input("x: ")
    masashiy = input("y: ")
    field[masashix][masashiy] = masashivalue



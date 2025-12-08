def howManyZeros(filename: str) -> int:
    point = 50
    move = point
    count = 0
    with open(filename, 'r') as file:
        for twist in file:
            if(twist[0] == 'L'):
                move -= int(twist[1:])
                point = (point - move) % 100
        
            elif(twist[0] == 'R'):
                move += int(twist[1:])
                point = (point + move) % 100

            if(point == 0):
                count += 1
    return count


if __name__ == "__main__":
    filename = "/Users/deepakgopal/Desktop/puzzle.txt"
    result = howManyZeros(filename)
    print(f"Number of times the point reached 0: {result}")

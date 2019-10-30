"""
 Disc #1 has 13 positions; at time=0, it is at position 10.
 Disc #2 has 17 positions; at time=0, it is at position 15.
 Disc #3 has 19 positions; at time=0, it is at position 17.
 Disc #4 has 7 positions; at time=0, it is at position 1.
 Disc #5 has 5 positions; at time=0, it is at position 0.
 Disc #6 has 3 positions; at time=0, it is at position 1.
"""

def part1():
    t = 0
    d1 = 10 + 1
    d2 = 15 + 2
    d3 = 17 + 3
    d4 = 1 + 4
    d5 = 0 + 5
    d6 = 1 + 6

    while True:
        d1 %= 13
        d2 %= 17
        d3 %= 19
        d4 %= 7
        d5 %= 5
        d6 %= 3

        if d1 == 0 and d2 == 0 and d3 == 0 and d4 == 0 and d5 == 0 and d6 == 0:
            break

        t += 1
        d1 += 1
        d2 += 1
        d3 += 1
        d4 += 1
        d5 += 1
        d6 += 1
        
    print(t)

def part2():
    t = 0
    d1 = 10 + 1
    d2 = 15 + 2
    d3 = 17 + 3
    d4 = 1 + 4
    d5 = 0 + 5
    d6 = 1 + 6
    d7 = 0 + 7

    while True:
        d1 %= 13
        d2 %= 17
        d3 %= 19
        d4 %= 7
        d5 %= 5
        d6 %= 3
        d7 %= 11

        if d1 == 0 and d2 == 0 and d3 == 0 and d4 == 0 and d5 == 0 and d6 == 0 and d7 == 0:
            break

        t += 1
        d1 += 1
        d2 += 1
        d3 += 1
        d4 += 1
        d5 += 1
        d6 += 1
        d7 += 1
        
    print(t)

part1()
part2()

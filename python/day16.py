def flip(b):
    if b == '0':
        return '1'
    else:
        return '0'
    
def dragon(initial_data, size_required):
    a = initial_data
    while len(a) < size_required:
        a = a + ['0'] + [flip(b) for b in reversed(a)]
    return a

def checksum(d):
    while len(d) % 2 == 0:
        c = []
        for i in range(0, len(d), 2):
            if d[i] == d[i+1]:
                c += ['1']
            else:
                c += ['0']
        d = c
    return "".join(d)
        

print("".join(dragon([b for b in "1"], 3)))        
print("".join(dragon([b for b in "0"], 3)))        
print("".join(dragon([b for b in "11111"], 11)))        
print("".join(dragon([b for b in "111100001010"], 25)))

print("part 1:", checksum(dragon([b for b in '11011110011011101'], 272)[0:272]))

print(checksum([b for b in '110010110100']))

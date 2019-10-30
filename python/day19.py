class Elf:
    num_elves = 0
    def __init__(self):
        self.next = self
        self.prev = self
        self.initial_position = 1
        Elf.num_elves += 1

    def add(self):
        e = Elf()
        e.prev = self
        e.next = self.next
        self.next.prev = e
        self.next = e
        e.initial_position = self.initial_position + 1
        return e

    def play(self, elf_across, elf_across_distance):
        """return the elf before the removed elf and the
            distance from self to that elf"""
        
        distance = Elf.num_elves // 2
        if elf_across_distance > distance:
            raise Exception("cant move backwards")

        while elf_across_distance < distance:
            elf_across = elf_across.next
            elf_across_distance += 1

        elf_before = elf_across.remove_and_return_previous()
        elf_across_distance -= 1
        return elf_before, elf_across_distance

    def __str__(self):
        return "Elf %d next=%d prev=%d" % (self.initial_position,
                self.next.initial_position,
                self.prev.initial_position)

    def is_last(self):
        return Elf.num_elves == 1

    def find_nth(self, n):
        e = self
        while n > 0:
            e = e.next
            n -= 1
        return e

    def find_across_circle(self):
        distance = Elf.num_elves // 2
        return self.find_nth(distance), distance
        
    def remove_and_return_previous(self):
        p = self.prev
        self.next.prev = self.prev
        self.prev.next = self.next
        self.next = self.prev = None
        Elf.num_elves -= 1
        return p


def play_game_2(n):
    playing_elf = make_elf_chain(n)
    elf_across, distance_to_elf_across = playing_elf.find_across_circle()
    while not playing_elf.is_last():
        elf_across, distance_to_elf_across = playing_elf.play(
            elf_across, distance_to_elf_across)
        playing_elf = playing_elf.next
        distance_to_elf_across -= 1
    return playing_elf


def print_elf_chain(elf1):
    print("Chain of %d" % Elf.num_elves)
    print(elf1)
    e = elf1.next
    while e != elf1:
        print(e)
        e = e.next

        
def make_elf_chain(n):
    Elf.num_elves = 0
    elf1 = Elf()
    end = elf1
    while Elf.num_elves < n:
        end = end.add()
    return elf1


print(play_game_2(5))
print(play_game_2(3017957))

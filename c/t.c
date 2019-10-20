#include <stdio.h>
#include <stdlib.h>

struct state_t {
	int floor;
};

int state_cmp(struct state_t* p_s1, struct state_t* p_s2) {
	if (p_s1->floor < p_s2->floor)
		return -1;
	else if (p_s1->floor > p_s2->floor)
		return 1;
	else
		return 0;
}

size_t child1(size_t pos) {
	return pos*2 + 1;
}
size_t child2(size_t pos) {
	return pos*2 + 2;
}
size_t parent(size_t pos) {
	return (pos-1)/2;
}

void heap_insert(struct state_t* p, struct state_t** heap, int* p_n) {
	int pos = *p_n;
	*p_n = pos+1;
	heap[pos] = p;

	while (pos != 0) {
		int parent_pos = parent(pos);
		if (state_cmp(heap[pos], heap[parent_pos]) <= 0) {
			break;
		}
		else {
			p = heap[pos];
			heap[pos] = heap[parent_pos];
			heap[parent_pos] = p;
			pos = parent_pos;
		}
	}
}

int main() {
	struct state_t** states = (struct state_t**) malloc(sizeof(struct state_t) * 100);
	int n = 0;
	for (int i=0; i < 100; ++i) {
		struct state_t* state = (struct state_t*) malloc(sizeof(struct state_t));
		state->floor = i;
		heap_insert(state, states, &n);
	}

	for (int i=0; i < n; ++i) {
		printf("%d %d\n", i, states[i]->floor);
	}
	return 0;
}

# Task

In the context of an HTTP application, create an endpoint that accepts a POST request with a JSON payload that
represents a tree data structure. The endpoint should return the maximum depth of the tree in the response. To solve
this challenge, you will need to implement a recursive function that traverses the tree and calculates its maximum
depth.

an example json could be:

```json
{
  "root": {
    "value": "A",
    "left": {
      "value": "B",
      "left": {
        "value": "D"
      },
      "right": {
        "value": "E"
      }
    },
    "right": {
      "value": "C",
      "right": {
        "value": "F"
      }
    }
  }
}
```

This represents a tree with a root node "A" that has two child nodes, "B" and "C". "B" has two child nodes, "D" and "E",
and "C" has one child node, "F". The maximum depth of this tree is 3, since the longest path from the root to a leaf
node is through the nodes "A" -> "B" -> "E".

an example JSON response body for this request could be:

```json
{
  "max_depth": 3
}
```

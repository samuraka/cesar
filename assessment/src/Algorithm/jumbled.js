/*
 * Worst case O(n)
 *
 */
const args = process.argv.slice(2);
console.log(args);

if (args.length === 0 || args.length === 1) {
  console.log("Call $ node jumbled.js <string1> <string2>");
  process.exit(1);
} else if (args.length > 2) {
  console.log("Only call node jumbled.js <string1> <string2>");
  process.exit(1);
}

const str = args[0];
const compStr = args[1];

function isJumbled(str1, str2) {
  let isPartialPermutation = false;
  let changes = 0;
  if (str1[0] === str2[0]) {
    for (let i = 1; i < str1.length; i++) {
      if (str1.indexOf(str1[i]) !== str2.indexOf(str2[i])) {
        changes++;
      }
    }
    if (changes <= 3) {
      isPartialPermutation = true;
    }
  }
  return isPartialPermutation;
}

console.log(isJumbled(str, compStr));
